/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Map;

import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;

/**
 * Combines a bind variable expression with another.
 *
 * @author Abe White
 */
class BindVariableAndExpression
    implements Exp {

    
    private static final long serialVersionUID = 1L;
    private final BindVariableExpression _bind;
    private final Exp _exp;

    /**
     * Constructor. Supply the two combined expressions.
     */
    public BindVariableAndExpression(BindVariableExpression bind, Exp exp) {
        _bind = bind;
        _exp = exp;
    }

    @Override
    public ExpState initialize(Select sel, ExpContext ctx, Map contains) {
        ExpState s1 = _bind.initialize(sel, ctx, contains);
        ExpState s2 = _exp.initialize(sel, ctx, contains);
        return new BinaryOpExpState(sel.and(s1.joins, s2.joins), s1, s2);
    }

    @Override
    public void appendTo(Select sel, ExpContext ctx, ExpState state,
        SQLBuffer buf) {
        boolean or = _exp instanceof OrExpression;
        if (or)
            buf.append("(");
        _exp.appendTo(sel, ctx, ((BinaryOpExpState) state).state2, buf);
        if (or)
            buf.append(")");
    }

    @Override
    public void selectColumns(Select sel, ExpContext ctx, ExpState state,
        boolean pks) {
        _exp.selectColumns(sel, ctx, ((BinaryOpExpState) state).state2, pks);
    }

    @Override
    public void acceptVisit(ExpressionVisitor visitor) {
        visitor.enter(this);
        _bind.acceptVisit(visitor);
        _exp.acceptVisit(visitor);
        visitor.exit(this);
    }
}
