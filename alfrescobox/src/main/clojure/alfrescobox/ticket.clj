(ns alfrescobox.ticket
  (:use [alfrescobox.util]))

(def ^:dynamic *ticket-size* (atom 25 :validator number?))

(defn gen-ticket
  "Generates a downloadable ticket"
  []
  (rand-str @*ticket-size*))
