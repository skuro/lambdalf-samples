(ns alfrescobox.webscript
  (:require [spring.surf.webscript :as w]
            [alfresco.auth :as a]
            [alfresco.search :as s]
            [alfresco.core :as c]))

(import '[spring.surf.webscript WebScript])

(deftype AlfrescoboxWebScript
  []
  WebScript
  (run [this in out model]
       (let [ticket (:ticket (w/template-args model))
             query (str "+ASPECT:\"abx:downloadable\" "
                        "+@abx\\:ticket:\"" ticket "\"")
             nodes (a/as-admin (s/query query))
             node (first nodes)]
         (w/return model {:contentNode (c/c2j node)}))))

(AlfrescoboxWebScript.)
