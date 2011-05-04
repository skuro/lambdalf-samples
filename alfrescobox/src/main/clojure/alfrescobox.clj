(ns alfrescobox
  (:require [alfresco.behave :as b]
            [alfresco.model :as m]
            [alfresco.nodes :as n]))

(def *ticket-size* (atom 25 :validator number?))

;; as seen here: http://blog.raphinou.com/2009/03/generate-random-string-in-clojure.html
(def VALID-CHARS
     (map char (concat (range 48 58) ; 0-9
                       (range 65 91) ; A-Z
                       (range 97 123))))

(defn rand-char []
  (rand-nth VALID-CHARS))

(defn rand-str [length]
  (apply str (take length (repeatedly rand-char))))

(defn gen-ticket
  []
  (rand-str @*ticket-size*))

(defn downloadable-aspect
  [node qname]
  (n/set-properties! node "abx:ticket" (gen-ticket)))

(gen-class :name  alfrescobox.Startup
           :prefix "abx-"
           :methods [[boot [] void]])

(defn abx-boot
  [this]
  (b/on-add-aspect! (m/qname "abx:downloadable") downloadable-aspect))
