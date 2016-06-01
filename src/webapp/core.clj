(ns webapp.core
  (:require [hiccup.page :refer [html5]]
            [hiccup.core :refer [h]]))

(defn html-body
  [title & contents]
  (html5
   [:head
    [:title (h title)]]
   (into [:body] contents)))

(defn page
  [title & contents]
  {:status 200
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :body (apply html-body title contents)})


(defn handler
  [r]
  (page "A webapp"
        [:h1 "Hello"]
        [:p "This is the standard response page"]))
