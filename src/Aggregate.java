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

public abstract class Aggregate {
    public abstract void start();
    public abstract void update(Tuple t);
    public abstract Tuple end();

    public static Aggregate min(String srcAttr, String destAttr) {
        return null;
    }

    public static Aggregate max(String srcAttr, String destAttr) {
        return null;
    }

    public static Aggregate avg(String srcAttr, String destAttr) {
        return null;
    }

    public static Aggregate sum(String srcAttr, String destAttr) {
        return null;
    }
}
