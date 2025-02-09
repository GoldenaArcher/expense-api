FROM openjdk:17

COPY backend/expense-tracker-api/target/expense_tracker_api.jar expense_tracker_api.jar

ENTRYPOINT [ "java", "-jar", "/expense_tracker_api.jar" ]