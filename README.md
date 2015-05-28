# Chattie

Using Services and passing info through Activities and Services:

src: http://lalit3686.blogspot.com.br/2012/06/how-to-update-activity-from-service.html

Saving data using Pointers in Parse:

ParseObject obj = new ParseObject("msg_chatest");
obj.put("userid", ParseObject.createWithoutData("Users", "7hKOJBhn7J"));
obj.put("message", edit.getText().toString());
obj.saveInBackground();

src: http://stackoverflow.com/questions/25556004/parse-how-to-set-a-pointer-in-android

Getting data using Pointers in Parse:

ParseQuery<ParseObject> query;
query = ParseQuery.getQuery("msg_chatest");
query.whereGreaterThan("updatedAt", sqlDate);
query.include("userid");
...
acessing: object.getParseObject("userid").getString("username")

src: http://stackoverflow.com/questions/15517541/how-to-query-value-of-column-that-is-set-as-pointer-to-other-table-in-parse
