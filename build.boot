(set-env!
 :source-paths #{"src/cljs"}
 :resource-paths #{"resources"}

 :dependencies
 '[[org.clojure/clojure                 "1.8.0"     :scope "provided"]
   [org.clojure/clojurescript           "1.9.89"    :scope "provided"]
   [adzerk/boot-cljs                    "1.7.228-1" :scope "test"]
   [pandeiro/boot-http                  "0.7.3"     :scope "test"]
   [adzerk/boot-reload                  "0.4.9"     :scope "test"]
   [adzerk/boot-cljs-repl               "0.3.2"     :scope "test"]
   [com.cemerick/piggieback             "0.2.1"     :scope "test"]
   [weasel                              "0.7.0"     :scope "test"]
   [org.clojure/tools.nrepl             "0.2.12"    :scope "test"]
   [boot/core                           "2.6.0"]
   [reagent                             "0.6.0-alpha2"]])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[pandeiro.boot-http :refer [serve]]
         '[adzerk.boot-reload :refer [reload]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]])


(deftask dev
  "Build for development"
  []
  (comp
    (serve :dir "dev/public")
    (watch)
    (reload)
    (cljs-repl)
    (cljs)
    (target :dir #{"dev"})))

(deftask prod
  "Build for production"
  []
  (comp
    (cljs
      :optimizations :advanced)
    (target :dir #{"prod"})))

