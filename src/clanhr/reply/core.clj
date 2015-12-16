(ns clanhr.reply.core
  (:import
    [java.io File])
  (:require [clanhr.reply.json :as json]
            [manifold.deferred :as d]
            [clojure.core.async :refer [go <!]]))

(defmacro async-reply
  [& reply]
  `(d/->deferred
    (go (let [response# (do ~@reply)
              body# (:body response#)]
          (assoc response# :body (<! (go body#)))))))

(defmacro async-result
  [& reply]
  `(async-reply (result (<! ~@reply))))

(defn data
  "Builds a response with the given data"
  ([status info]
   (data status info "application/json"))
  ([status info content-type]
   {:status status
    :headers {"Content-Type" content-type}
    :body (json/dump info)}))

(defn file
  "Builds a response for a given file path"
  [file-path file-name content-type]
  (let [file-response (File. file-path)]
    {:status 200
     :headers {"Content-Type" content-type
               "Content-Disposition" (str "attachment; filename=\"" file-name "\"")}
     :body file-response}))

(defn excel-file
  "Builds a response from an excel file"
  [file-path file-name]
  (file file-path
        file-name
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))

(defn plain-data
  [status info content-type]
  {:status status
   :headers {"Content-Type" content-type}
   :body info})

(defn ok-ical
  [info]
  (plain-data 200 info "text/calendar"))

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

(defn forbidden
  "Builds a response with the given data and creates status code"
  ([]
   (forbidden {}))
  ([info]
  (data 403 info)))

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
  (cond
    (:success result) (ok result)
    (:forbidden result) (forbidden result)
    (:unauthorised result) (unauthorized result)
    (:exception result) (exception (:exception result))
    :else (bad-request result)))
