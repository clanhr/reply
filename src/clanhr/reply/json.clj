(ns clanhr.reply.json
  (:require [cheshire.core :refer :all]
            [cheshire.generate :refer [add-encoder encode-str]]))

(defn dump
  "Dumps the given object as json"
  [data]
  (generate-string data))

(defn build
  "Loads the json as edn"
  [raw]
  (parse-string raw true))
