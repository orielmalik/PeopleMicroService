from flask import Flask, request, jsonify
from SERVICE.ValidateEmail import check_email_exists

app = Flask(__name__)

@app.route('/emails', methods=['GET'])
def get_emails():
    email = request.args.get("email")
    exists, message = check_email_exists(email)
    print(message)
    return str(exists)

if __name__ == '__main__':
    app.run(debug=True)
