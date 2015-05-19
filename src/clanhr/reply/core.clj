(ns clanhr.reply.core
  (:require [clanhr.reply.json :as json]))

(defn data
  "Builds a response with the given data"
  [status info]
  {:status status
   :headers {"Content-Type" "application/json"}
   :body (json/dump info) })

(defn ok
  "Builds a response with the given data and creates status code"
  [info]
  (data 200 info))

(defn created
  "Builds a response with the given data and creates status code"
  [info]
  (data 201 info))

(defn saved
  "Builds a response for a created or updated model"
  [new? info]
  (if new?
    (created info)
    (ok info)))

(defn accepted
  "Builds a response with the given data and creates status code"
  [info]
  (data 202 info))

(defn bad-request
  "Builds a response with the given data and creates status code"
  [info]
  (data 400 info))

(defn unauthorized
  "Builds a response with the given data and creates status code"
  ([]
   (unauthorized {}))
  ([info]
  (data 401 info)))

(defn not-found
  "Builds a response with the given data and creates status code"
  [info]
  (data 404 info))

(defn internal-server-error
  "Builds a response with the given data and creates status code"
  [info]
  (data 500 info))

(defn exception
  "Builds a response for an exception"
  [ex]
  (internal-server-error {:error (.getMessage ex)}))

(defn result
  "Builds a response for a clanhr's result"
  [result]
  (if (= true (:success result))
    (ok result)
    (bad-request result)))
