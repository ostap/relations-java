/*
Copyright 2011 Ostap Cherkashin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package org.bandilab;

import java.util.*;

public final class Relation {
    private final Head head;
    private final Set<Tuple> body;

    public Relation(Attribute ... attrs) {
        this.head = new Head(attrs);
        this.body = new TreeSet<Tuple>(new TupleCmp(this.head.getIdx()));
    }

    private Relation(Head head, Set<Tuple> body) {
        this.head = head;
        this.body = body;
    }

    public static Relation join(Relation left, Relation right) {
        return null;
    }

    public void add(Comparable ... tuple) {
        // TODO: check the attribute types match the attribute values
        body.add(new Tuple(tuple));
    }

    public int size() {
        return body.size();
    }

    public String toString() {
        return body.toString();
    }

    /** project(employees, "firstName", "lastName"); */
    public static Relation project(Relation r, String ... attrs) {
        return null;
    }

    /** summary(employees, cnt("count"), avg("average")); */
    public static Relation summary(Relation r, Aggregate ... aggrs) {
        return null;
    }

    /** extend(ticker, "sqDiff", pow(minus("close", "avg"), 2)); */
    public static Relation extend(Relation r, String attr, Extension ext) {
        return null;
    }

    /** sort(ticker, "close"); */
    public static Relation sort(Relation r, String ... attrs) {
        TupleCmp cmp = new TupleCmp(r.head.getIdx(attrs));
        TreeSet<Tuple> sorted = new TreeSet<Tuple>(cmp);
        sorted.addAll(r.body);

        return new Relation(r.head, sorted);
    }
}
