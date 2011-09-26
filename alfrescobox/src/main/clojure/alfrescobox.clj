(ns alfrescobox
  (:require [alfresco.actions :as a])
  (:import [alfresco.actions Action]))

(defn- email-action-impl [_ action node]
  (println "Recipients are:")
  (println (str "xxxx" (bean action)))
  (map println (seq (.getParameterValue action "to"))))

;; Define a new Action class that can be used in Spring initialization
(defrecord EmailTicketAction []
    Action
    (needs-params [this]
      [(a/param {:name "to"
                 :type "d:text"
                 :mandatory true
                 :multi true
                 :label "email-ticket"})])
    
    (exec [this action node]
      (email-action-impl this action node)))
