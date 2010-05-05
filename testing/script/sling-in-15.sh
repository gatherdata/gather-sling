echo "creating mynode"
curl -F"sling:resourceType=foo/bar" -F"title=some title" http://admin:admin@localhost:8090/content/mynode

echo "creating path for rendering script"
curl -X MKCOL http://admin:admin@localhost:8090/apps/foo
curl -X MKCOL http://admin:admin@localhost:8090/apps/foo/bar

echo "posting rendering script html.esp"
curl -T html.esp http://admin:admin@localhost:8090/apps/foo/bar/html.esp

echo "getting the rendered node"
curl http://localhost:8090/content/mynode.html

