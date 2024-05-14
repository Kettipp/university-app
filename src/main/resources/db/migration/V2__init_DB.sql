ALTER TABLE class
    ADD CONSTRAINT unique_class_day_time_group UNIQUE (day, time, group_id);