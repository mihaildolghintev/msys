(defproject msys "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [mount "0.1.16"]
                 [ring "1.9.2"]
                 [ring/ring-mock "0.4.0"]
                 [ring/ring-json "0.5.1"]
                 [compojure "1.6.2"]
                 [clojure.java-time "0.3.2"]
                 [com.novemberain/monger "3.1.0"]
                 [cheshire "5.10.0"]
                 ]
  :repl-options {:init-ns msys.core})
