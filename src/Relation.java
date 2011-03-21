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

    /**
     * Here is how to construct a new Books relation with three attributes
     * (title, pages, and price):
     *
     * <code>
     * Relation books = new Relation(
     *         new Attribute("title", String.class),
     *         new Attribute("pages", Integer.class),
     *         new Attribute("price", Double.class));
     *
     * r.add("The Iliad", 576, 26.40);
     * r.add("The Odyssey", 416, 19.95);
     * r.add("Oliver Twist", 608, 12.99);
     * </code>
     */
    public Relation(Attribute ... attrs) {
        this(new Head(attrs));
    }

    private Relation(Head head) {
        this(head, new TreeSet<Tuple>(new TupleCmp(head.getIndex())));
    }

    private Relation(Head head, Set<Tuple> body) {
        this.head = head;
        this.body = body;
    }

    public void add(Comparable ... vals) {
        // TODO: check the attribute types match the attribute values
        body.add(new Tuple(head, vals));
    }

    public int size() {
        return body.size();
    }

    public String toString() {
        return body.toString();
    }

    /**
     * Select tuples according to criteria. E.g. select books where name
     * contains word "robot":
     *
     * <code>
     * Relation roboBooks =
     *     books.select(new Criteria() {
     *         public boolean take(Tuple t) {
     *             return t.getString("title").contains("robot");
     *         }});
     * </code>
     */
    public Relation select(Criteria c) {
        Relation res = new Relation(this.head);

        for (Tuple t : this.body)
            if (c.take(t))
                res.body.add(t);

        return res;
    }

    /**
     * Extend a relation with more attributes. This operator can be used to
     * produce general calculations on a tuple level. E.g. extend the
     * books with the newPrice which is a 10 percent reduction of the current
     * price:
     *
     * <code>
     * Relation salePrices =
     *     books.extend(new Extension("newPrice", Double.class) {
     *         public Comparable extend(Tuple t) {
     *             return 0.90 * t.getDouble("price");
     *         }
     *     });
     * </code>
     */
    public Relation extend(Extension ... ext) {
        Relation res = new Relation(this.head.extend(ext));

        for (Tuple t : this.body) {
            Comparable[] vals = new Comparable[res.head.size()];

            int i = 0;
            for (Comparable c : t.vals)
                vals[i++] = c;

            for (Extension e : ext)
                vals[i++] = e.extend(t);

            res.body.add(new Tuple(res.head, vals));
        }

        return res;
    }

    /**
     * Project a relation to the specified attributes. E.g. project the books
     * relation to titles (see below) produces a unique set of book titles:
     *
     * <code>
     * Relation allTitles = books.project("titles");
     * </code>
     */
    public Relation project(String ... attrs) {
        Relation res = new Relation(this.head.project(attrs));

        int[] index = this.head.getIndex(attrs);
        for (Tuple t : this.body) {
            Comparable[] vals = new Comparable[attrs.length];

            int i = 0;
            for (int pos : index)
                vals[i++] = t.vals[pos];

            res.body.add(new Tuple(res.head, vals));
        }

        return res;
    }

    /**
     * <code>
     * Relation myBooks = books.join(selectedTitles);
     * </code>
     */
    public Relation join(Relation otherRelation) {
        return null;
    }

    /**
     * <code>
     * books.summary(cnt("count"));
     * </code>
     */
    public Relation summary(Aggregate ... aggrs) {
        return null;
    }

    /**
     * Sorts a relation by given attributes (ascending). E.g. sort the books
     * by price:
     *
     * <code>
     * books.sort("price");
     * </code>
     */
    public Relation sort(String ... attrs) {
        TupleCmp cmp = new TupleCmp(this.head.getSortIndex(attrs));
        TreeSet<Tuple> sorted = new TreeSet<Tuple>(cmp);
        sorted.addAll(this.body);

        return new Relation(this.head, sorted);
    }
}
