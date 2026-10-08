import mysql.connector
import datetime

def update_weather_data():
    try:
        # Establish connection to your local MySQL
        db = mysql.connector.connect(
            host="localhost",
            user="root",
            password="Pass@123python3 fetch_data.py",  # <-- Replace with your MySQL password
            database="tanker_db"
        )
        cursor = db.cursor()

        # Generate sample 7-day forecast data (Days, Rainfall in mm, Temp in C)
        today = datetime.date.today()
        forecast_data = [
            (today + datetime.timedelta(days=i), 0.0 if i < 5 else 15.0, 32.0 + i)
            for i in range(7)
        ]

        # Insert forecast into the database
        sql = "INSERT INTO weather_forecast (forecast_date, expected_rainfall_mm, temperature_celsius) VALUES (%s, %s, %s)"
        cursor.executemany(sql, forecast_data)
        db.commit()

        print("✔ Python: Weather forecast successfully logged to MySQL.")

    except mysql.connector.Error as err:
        print(f"Database Error: {err}")
    finally:
        if 'db' in locals() and db.is_connected():
            cursor.close()
            db.close()

if __name__ == "__main__":
    update_weather_data()