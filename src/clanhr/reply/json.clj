(ns clanhr.reply.json
  (:require [cheshire.core :refer :all]
            [cheshire.generate :refer [add-encoder encode-str]]
            [clj-time.coerce :as coerce]))

(add-encoder
  org.joda.time.DateTime
  (fn [data jsonGenerator]
    (.writeString jsonGenerator (coerce/to-string data))))

(defn dump
  "Dumps the given object as json"
  [data]
  (generate-string data))

(defn build
  "Loads the json as edn"
  [raw]
  (parse-string raw true))

