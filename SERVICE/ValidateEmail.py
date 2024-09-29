import smtplib
import dns.resolver


def check_email_exists(email):
    # חלוקת האימייל ל-username ו-domain
    domain = email.split('@')[1]

    # קבלת רשומות ה-MX של הדומיין
    try:
        records = dns.resolver.resolve(domain, 'MX')
        mx_record = str(records[0].exchange)
    except (dns.resolver.NoAnswer, dns.resolver.NXDOMAIN):
        return False, "Domain does not exist"

    # בדיקה מול שרת ה-SMTP
    server = smtplib.SMTP()
    server.set_debuglevel(0)

    try:
        server.connect(mx_record)
        server.helo(server.local_hostname)  # שלב הזדהות
        server.mail('test@example.com')  # כתובת פיקטיבית למשלוח
        code, message = server.rcpt(email)  # בדיקה אם הנמען קיים

        if code == 250:
            return True, "Email exists"
        else:
            return False, "Email does not exist"

    except smtplib.SMTPServerDisconnected:
        return False, "Connection to SMTP server failed"

    finally:
        server.quit()


email = 's@gmail.com'
exists, message = check_email_exists(email)
print(f"{email}: {message}")
