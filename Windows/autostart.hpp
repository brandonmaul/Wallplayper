#ifndef AUTOSTART_H
#define AUTOSTART_H



std::wstring get_current_directory_on_windows(void);
int8_t run_application_on_boot(void);
int8_t test_status(LONG create_status ,LONG value_status, LONG close_status);

#endif // AUTOSTART_H
