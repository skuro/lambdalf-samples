(comment
  ;; NOTE: ns declaration is useless for Clojure web scripts
  (ns alfrescobox))

(require '[spring.surf.webscript :as w]
         '[alfresco.auth :as a]
         '[alfresco.search :as s]
         '[alfresco.core :as c])

(import '[spring.surf.webscript WebScript])

(deftype AlfrescoboxWebScript
  WebScript
  (run [this model]
       (let [nodes (a/as-admin (s/query "+ASPECT:\"abx:downloadable\" +@abx\:ticket:\"wtf\""))
             node (first nodes)]
         (w/return model {:contentNode (c/c2j node)}))))
