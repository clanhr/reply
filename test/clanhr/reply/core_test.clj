(ns clanhr.reply.core-test
  (use clojure.test)
  (:require [clanhr.reply.core :as reply]))

(deftest ok-test
  (let [data {:company "ClanHR"}
        response (reply/ok data)]
  (is (= 200 (:status response)))))

(deftest created-test
  (let [data {:company "ClanHR"}
        response (reply/created data)]
  (is (= 201 (:status response)))))

(deftest accepted-test
  (let [data {:company "ClanHR"}
        response (reply/accepted data)]
  (is (= 202 (:status response)))))

(deftest bad-request-test
  (let [data {:company "ClanHR"}
        response (reply/bad-request data)]
  (is (= 400 (:status response)))))

(deftest unauthorized
  (let [response (reply/unauthorized)]
  (is (= 401 (:status response)))))

(deftest not-found
  (let [data {:company "ClanHR"}
        response (reply/not-found data)]
  (is (= 404 (:status response)))))

(deftest internal-server-error
  (let [data {:company "ClanHR"}
        response (reply/internal-server-error data)]
  (is (= 500 (:status response)))))
