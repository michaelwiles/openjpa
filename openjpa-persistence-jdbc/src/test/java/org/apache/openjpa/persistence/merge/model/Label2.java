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
package org.apache.openjpa.persistence.merge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.apache.openjpa.persistence.DetachedState;
import org.apache.openjpa.persistence.jdbc.ForeignKey;

@Entity
@Table(name = "MRG_LABEL2")
@TableGenerator(name = "Label2Gen", allocationSize = 10, pkColumnValue = "MRG_LABEL2")
public class Label2 {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Label2Gen")
    @Column(name = "LABEL_ID")
    private long id;

    @OneToOne
    @ForeignKey
    @JoinColumn(name = "PKG_ID")
    private ShipPackage2 pkg;

    @SuppressWarnings("unused")
    @Version
    private int version;

    @SuppressWarnings("unused")
    @DetachedState
    private Object state;

    public Label2(ShipPackage2 p) {
        setPackage2(p);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setPackage2(ShipPackage2 p) {
        pkg = p;
    }

    public ShipPackage2 getPackage2() {
        return pkg;
    }
}
