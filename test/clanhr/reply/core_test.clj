(ns clanhr.reply.core-test
  (use clojure.test)
  (:require [clanhr.reply.core :as reply]
            [manifold.deferred :as d]
            [clojure.core.async :refer :all]
            [clj-time.core :as t]))

(deftest ok-test
  (let [data {:company "ClanHR"}
        response (reply/ok data)]
    (is (= 200 (:status response)))))

(deftest created-test
  (let [data {:company "ClanHR"}
        response (reply/created data)]
    (is (= 201 (:status response)))))

(deftest saved-test
  (let [data {:company "ClanHR"}
        response (reply/saved true data)]
    (is (= 201 (:status response))))
  (let [data {:company "ClanHR"}
        response (reply/saved false data)]
    (is (= 200 (:status response)))))

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

(deftest render-dates
  (let [data {:date (t/now)}
        response (reply/ok data)]
    (is (= 200 (:status response)))))

(deftest result-test
  (let [response-ok (reply/result {:success true})
        response-unauthorised (reply/result {:unauthorised true})
        response-bad (reply/result {:success false})]
    (is (= 200 (:status response-ok)))
    (is (= 401 (:status response-unauthorised)))
    (is (= 400 (:status response-bad)))))

(deftest async-reply-test
  (let [response @(reply/async-reply (reply/ok 1))]
    (is (= 200 (:status response)))
    (is (= "1" (:body response)))))

(deftest async-result-test
  (testing "ok"
    (let [response @(reply/async-result (go {:success true}))]
      (is (= 200 (:status response)))))

  (testing "unauthorized"
    (let [response @(reply/async-result (go {:unauthorised true}))]
      (is (= 401 (:status response)))))

  (testing "bad request"
    (let [response @(reply/async-result (go {:anything true}))]
      (is (= 400 (:status response)))))
  )
