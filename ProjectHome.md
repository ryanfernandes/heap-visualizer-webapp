A drop-in web application (.war) to visualize heap statistics<br>

Consists of:<br>
<ul><li>A Servlet that uses the Java Management API to introspect JVM Heap (and Non Heap) statistics and return a JSON object to render<br>
</li><li>An HTML to render the JSON object using jQuery</li></ul>

<a href='http://tripoverit.blogspot.com/2011/09/heap-visualizer-web-app.html'>Screenshots and instructions here</a>

<hr />
Libraries used:<br>
<ul><li>jQuery (<a href='http://jquery.org'>http://jquery.org</a>)<br>
</li><li>jQuery Sparklines  (<a href='http://omnipotent.net/jquery.sparkline'>http://omnipotent.net/jquery.sparkline</a>)<br>
</li><li>Jackson JSON Processor  (<a href='http://jackson.codehaus.org'>http://jackson.codehaus.org</a>)<br>
<hr />
Requires: JDK 1.5+