(ns alfrescobox
  (:require [alfresco.behave :as b]
            [alfresco.model :as m]
            [alfresco.nodes :as n]
            [alfresco.actions :as act])
  (:import [alfresco.actions Action]))

(def *ticket-size* (atom 25 :validator number?))

;; as seen here: http://blog.raphinou.com/2009/03/generate-random-string-in-clojure.html
(def VALID-CHARS
     (map char (concat (range 48 58) ; 0-9
                       (range 65 91) ; A-Z
                       (range 97 123))))

(defn rand-char
  "Picks a random character from the available sets"
  []
  (rand-nth VALID-CHARS))

(defn rand-str
  "Generates a random string of the given length"
  [length]
  (apply str (take length (repeatedly rand-char))))

(defn gen-ticket
  "Generates a downloadable ticket"
  []
  (rand-str @*ticket-size*))

(defn downloadable-aspect
  "Applies the abx:downloadable aspect including a randomly generated ticket property"
  [node qname]
  (n/set-properties! node "abx:ticket" (gen-ticket)))

;; Generates bootstrap/initialization code
(gen-class :name  alfrescobox.Startup
           :prefix "abx-"
           :methods [[boot [] void]])

(defn abx-boot [this]
  (b/on-add-aspect! (m/qname "abx:downloadable") downloadable-aspect))
;;

(defn- email-action-impl [_ action node]
  (println "Recipients are:")
  (println (str "xxxx" (bean action)))
  (map println (seq (.getParameterValue action "to"))))

;; Define a new Action class that can be used in Spring initialization
(defrecord EmailTicketAction []
    Action
    (needs-params [this]
      [(act/param {:name "to"
                   :type "d:text"
                   :mandatory true
                   :multi true
                   :label "email-ticket"})])
    
    (exec [this action node]
      (email-action-impl this action node)))
