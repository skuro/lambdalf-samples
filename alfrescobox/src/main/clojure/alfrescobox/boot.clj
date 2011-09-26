(ns alfrescobox.boot
  (:require [alfresco.behave :as b]
            [alfresco.model :as m]))

(gen-class :name  alfrescobox.Startup
           :prefix "abx-"
           :methods [[boot [] void]])

(defn abx-boot [this]
  (b/on-add-aspect! (m/qname "abx:downloadable") downloadable-aspect))
