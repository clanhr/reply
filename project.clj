(defproject clanhr/reply "0.1.0"
  :description "FIXME: write description"
  :url "http://github.com/clanhr/reply"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0-beta2"]
                 [cheshire "5.4.0"]]
  :profiles {:1.7 {:dependencies [[org.clojure/clojure "1.7.0-beta2"]]}}
  :aliases {"all" ["with-profile" "dev:1.7" "test"]}) 
