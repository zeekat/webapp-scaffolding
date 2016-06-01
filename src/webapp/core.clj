(ns webapp.core
  (:require [hiccup.page :refer [html5 include-css]]
            [hiccup.core :refer [h]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn html-body
  [title & contents]
  (html5
   [:head
    [:title (h title)]
    (include-css "https://fonts.googleapis.com/css?family=Roboto:300,300italic,700,700italic")
    (include-css "https://cdnjs.cloudflare.com/ajax/libs/normalize/3.0.3/normalize.css")
    (include-css "assets/milligram.min.css")]
   (into [:body] contents)))

(defn page
  [title & contents]
  {:status 200
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :body (apply html-body title contents)})

(defn handler
  [request]
  (page "A webapp"
        [:h1 "Hello"]
        [:p "This is the standard response page"]))

(def handler-with-middleware
  (-> handler
      (wrap-defaults site-defaults)))
