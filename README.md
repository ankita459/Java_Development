To test the RESTful API endpoints using Postman, you can follow these steps:

**1. Authentication Endpoint:**
Use the POST method.
Set the URL to your authentication endpoint: https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp.
Set the body to raw and select JSON.
Add the following JSON payload:
json
Copy code
{
  "login_id": "test@sunbasedata.com",
  "password": "Test@123"
}
Send the request and capture the received Bearer token from the response.

**2. Create Customer Endpoint:**
Use the POST method.
Set the URL to your create customer endpoint: https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=create.
Set the headers:
Authorization: Bearer YOUR_RECEIVED_TOKEN
Content-Type: application/json
Set the body to raw and select JSON.
Add the customer details JSON payload:
json
Copy code
{
  "first_name": "Jane",
  "last_name": "Doe",
  "street": "Elvnu Street",
  "address": "H no 2",
  "city": "Delhi",
  "state": "Delhi",
  "email": "sam@gmail.com",
  "phone": "12345678"
}
Send the request and check the response for success or error messages.

**3. Get Customer List Endpoint:**
Use the GET method.
Set the URL to your get customer list endpoint: https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list.
Set the headers:
Authorization: Bearer YOUR_RECEIVED_TOKEN
Send the request and check the response for the list of customers.

**4. Delete Customer Endpoint:**
Use the POST method.
Set the URL to your delete customer endpoint: https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=delete&uuid=SPECIFIC_CUSTOMER_UUID.
Set the headers:
Authorization: Bearer YOUR_RECEIVED_TOKEN
Content-Type: application/json
Send the request and check the response for success or error messages.

**5. Update Customer Endpoint:**
Use the POST method.
Set the URL to your update customer endpoint: https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=update&uuid=SPECIFIC_CUSTOMER_UUID.
Set the headers:
Authorization: Bearer YOUR_RECEIVED_TOKEN
Content-Type: application/json
Set the body to raw and select JSON.
Add the updated customer details JSON payload:
json
Copy code
{
  "first_name": "Updated Jane",
  "last_name": "Updated Doe",
  "street": "Updated Elvnu Street",
  "address": "Updated H no 2",
  "city": "Updated Delhi",
  "state": "Updated Delhi",
  "email": "updatedsam@gmail.com",
  "phone": "98765432"
}
Send the request and check the response for success or error messages.

Make sure to replace YOUR_RECEIVED_TOKEN with the actual token received during authentication, and replace SPECIFIC_CUSTOMER_UUID with the UUID of the customer you want to update or delete.

By following these steps, you can test the entire Spring Boot API using Postman.
