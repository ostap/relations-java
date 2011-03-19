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
        Relation r = new Relation(
            new Attribute("greeting", String.class),
            new Attribute("price", Integer.class),
            new Attribute("temperatur", Double.class));

        r.add("hello", 123, 12.25);
        r.add("world", 321, 25.12);

        System.out.println(r);
        System.out.println(sort(r, "greeting"));
    }
}
