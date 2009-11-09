gather-sling - sandbox
======================
A developer's sandbox for trying out new ideas.

Development Cycle
-----------------
1. run sling from project root: `mvn clean install pax:provision`
2. switch to this module: `cd sling-sandbox`
3. make changes
4. deploy to running sling: `mvn clean install sling:install`
5. go to #3
