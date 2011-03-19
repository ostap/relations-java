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

final class Head {
    private final Map<String, Integer> index;
    private final Map<String, Class> types;

    Head(Attribute attrs[]) {
        this.index = new HashMap<String, Integer>();
        this.types = new HashMap<String, Class>();

        int i = 0;
        for (Attribute a : attrs) {
            this.index.put(a.name, i++);
            this.types.put(a.name, a.type);
        }
    }

    int[] getIndex(String ... attrs) {
        Collection<String> x = (attrs.length == 0)
            ? index.keySet()
            : Arrays.asList(attrs);


        int i = 0;
        int res[] = new int[x.size()];
        for (String a : x)
            res[i++] = index.get(a); // FIXME: what if not found?

        return res;
    }
}
