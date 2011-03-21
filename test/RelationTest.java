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

public final class RelationTest {
    public static void main(String[] args) throws Exception {
        Relation r = xyz();
        checkEq("[[1, z], [11, y], [111, x], [1111, x]]", r);

        testSort();
        testProject();
        testSelect();
        testExtend();
    }

    static void testSort() {
        Relation r = xyz();

        checkEq("[[1, z], [11, y], [111, x], [1111, x]]", r.sort("id"));
        checkEq("[[111, x], [1111, x], [11, y], [1, z]]", r.sort("name"));
    }

    static void testProject() {
        Relation r = xyz();

        checkEq("[[x], [y], [z]]", r.project("name"));
        checkEq("[[1], [11], [111], [1111]]", r.project("id"));
        checkEq("[[1, z], [11, y], [111, x], [1111, x]]",
                r.project("id", "name"));
        checkEq("[[x, 111], [x, 1111], [y, 11], [z, 1]]",
                r.project("name", "id"));
    }

    static void testSelect() {
        Relation r = xyz();

        checkEq("[[111, x], [1111, x]]",
                r.select(new Criteria() {
                    public boolean take(Tuple t) {
                        return t.getInt("id") > 11;
                    }
                }));
        checkEq("[[1, z], [11, y]]",
                r.select(new Criteria() {
                    public boolean take(Tuple t) {
                        return !t.getString("name").equals("x");
                    }
                }));

        boolean failed = false;
        try {
            r.select(new Criteria() {
                public boolean take(Tuple t) {
                    return t.getInt("doesNotExist") > 0;
                }});
        } catch (IllegalArgumentException e) {
            failed = true;
        }

        if (!failed)
            throw new AssertionError();
    }

    static void testExtend() {
        Relation r = xyz();

        checkEq("[[1, z, 2], [11, y, 22], [111, x, 222], [1111, x, 2222]]",
                r.extend(new Extension("twiceId", Integer.class) {
                    public Comparable extend(Tuple t) {
                        return 2 * t.getInt("id");
                    }
                }));
        checkEq("[[1, z, z1], [11, y, y11], [111, x, x111], [1111, x, x1111]]",
                r.extend(new Extension("nameAndId", String.class) {
                    public Comparable extend(Tuple t) {
                        return t.getString("name") + t.getInt("id");
                    }
                }));
    }

    static void checkEq(String expected, Relation r) {
        if (!expected.equals(r.toString()))
            throw new AssertionError();
    }

    static Relation xyz() {
        Relation r = new Relation(
                new Attribute("id", Integer.class),
                new Attribute("name", String.class));

        r.add(1111, "x");
        r.add(111,  "x");
        r.add(11,   "y");
        r.add(1,    "z");

        return r;
    }
}
