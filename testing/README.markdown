testing
=======

Live testing of gather-sling using curl to exercise
the REST interfaces.

REST API
--------

### gather-forms

* /gather/rosa/forms.xml - get JavaRosa-friendly list of forms
* /gather/rosa/form/$group/$name/form.xml - get JavaRosa-compatible version of form
* /gather/rosa/data.xml - post JavaRosa form instance data

* /gather/orbeon/

* /gather/forms/form.xml - form template api calls
* /gather/forms/form/<uid> - get an individual form

### gather-alert

* /gather/alert/plan.xml - alert plan api calls
* /gather/alert/plan/<uid> - get an individual plan

Curl Test
---------

* Post sample xml data
    `curl --verbose --data @resources/simple.xml --header "Content-Type: text/xml" --header "Content-Encoding: UTF-8" http://localhost:8090/gather/rosa/data.xml`


Fun with Nodes
--------------
The JCR nodes can be search using the JSON Query Servlet.

* Children of /etc/links showing title and url properties
    `curl "http://localhost:8090/content.query.json?queryType=xpath&statement=//etc/links/*&property=title&property=url"`
