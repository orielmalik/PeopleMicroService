# השתמש ב-image בסיסי של Python
FROM python:3.9-slim

# הגדר משתנה סביבה כך ש-Flask יוכל לעבוד במצב production
ENV FLASK_APP=app.py

# העתק את קבצי האפליקציה ל-image
WORKDIR /app
COPY . /app

# התקן את התלויות של האפליקציה מ-requirements.txt
RUN pip install --no-cache-dir -r requirements.txt

# הפורט שבו Flask רץ
EXPOSE 5000

# הפעל את Flask server כאשר container מתחיל
CMD ["flask", "run", "--host=0.0.0.0"]
