gather-sling - Sling-based Web Application
==========================================
[Apache Sling][1] is a fun OSGi web application framework -- a scriptable JCR
content repository with a RESTful presence. 

Building
--------

gather-sling uses the Neo4j apoc-bundle for provisioning neo4j. This component
has not yet been released into the public repository, so it must be downloaded
and built first. 

1. `svn co https://svn.neo4j.org/laboratory/components/apoc-bundle`
2. `cd apoc-bundle`
3. `mvn clean install`

Then build gather-sling from the project base directory.

1. `cd gather-sling`
2. `mvn clean install`

Running
-------

`mvn pax:provision`

References
----------
* [Apache Sling][1] - web framework
* [Neo4j][2] - graph database

[1]: http://sling.apache.org    "Apache Sling"
[2]: http://neo4j.org/          "Neo4j"
