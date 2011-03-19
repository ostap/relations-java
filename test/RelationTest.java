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

import static org.bandilab.Relation.*;

public final class RelationTest {
    public static void main(String[] args) throws Exception {
        testSort();
    }

    static void testSort() {
        Relation r = new Relation(
                new Attribute("id", Integer.class),
                new Attribute("name", String.class));

        r.add(111, "x");
        r.add(11,  "y");
        r.add(1,   "z");

        if (!"[[1, z], [11, y], [111, x]]".equals(r.toString()))
            throw new AssertionError();
        if (!"[[111, x], [11, y], [1, z]]".equals(sort(r, "name").toString()))
            throw new AssertionError();
    }
}
