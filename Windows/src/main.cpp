#include <iostream>
#include <wchar.h>
#include <windows.h>
#include "autostart.hpp"

using std::cout;
using std::endl;
using std::wstring;

int main()
{
    int8_t error = run_application_on_boot();
    return (int) error;
}

std::wstring get_current_directory_on_windows()
{
    const unsigned long max_dir = 260;
    WCHAR current_dir[max_dir];
    GetCurrentDirectoryW(max_dir, current_dir);
    return std::wstring(current_dir);
}

int8_t run_application_on_boot()
{
    HKEY hkey;
    std::wstring prog_path = get_current_directory_on_windows() +L"\\Wallplayper.jar\0";
    LONG create_status = RegCreateKeyW(HKEY_CURRENT_USER, L"Software\\Microsoft\\Windows\\CurrentVersion\\Run", &hkey); //Creates a key
    LONG value_status = RegSetValueExW(hkey, L"Wallplayper", 0, REG_SZ, (BYTE *)prog_path.c_str(), (prog_path.size()+1) * sizeof(wchar_t));
    LONG close_key_status = RegCloseKey(hkey);

    return test_status(create_status, value_status, close_key_status);
}

int8_t test_status(LONG create_status ,LONG value_status, LONG close_status)
{
    int8_t result = 0;
    if (create_status == ERROR_SUCCESS){
        cout<<"Success creating key."<< endl;
    }else{
        cout<<"Error creating key." << endl;
        result += -1;
    }

    if (value_status == ERROR_SUCCESS){
        cout<< "Success creating value name and value data for our key." << endl;
    }else{
        cout<<"Error creating value name and value data for our key." << endl;
        result += -2;
    }

    if (close_status == ERROR_SUCCESS){
        cout << "Success closing our new registry entry." << endl;
    }else{
        cout<< "Error closing our new registry entry."<<endl;
        result += -3;
    }
    return result;
}

