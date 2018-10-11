#include <iostream>
#include <wchar.h>
#include <windows.h>
#include "autostart.hpp"

using std::cout;
using std::endl;
using std::wstring;

int main()
{
    //int8_t error0 = run_application_on_boot();
    int8_t error1 = disable_start_on_boot();
    return (int) error1;
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

    return test_creation_status(create_status, value_status, close_key_status);
}

int8_t disable_start_on_boot(void){
    HKEY hKey;
    LONG open_status = RegOpenKeyExA(HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Run", 0, 0xF003Fu, &hKey);
    LONG delete_status = RegDeleteValue(hKey, "Wallplayper");
    LONG close_key_status = RegCloseKey(hKey);
    return test_deletion_status(open_status, delete_status, close_key_status);

}

int8_t test_deletion_status(LONG open_status, LONG delete_status, LONG close_status){
    int8_t result_code = 0;

    if(open_status == ERROR_SUCCESS){
        cout << "Success opening key."<< endl;
    } else {
        cout << "Failed to open the required key." << endl;
        result_code += -1;
    }

    if(delete_status == ERROR_SUCCESS){
        cout << "Success deleting value."<< endl;
    } else {
        cout << "Failed to delete value. (does the value exist?)" << endl; // if the value does not exist it will return an error code.
        result_code += -2;
    }

    if (close_status == ERROR_SUCCESS){
        cout << "Success closing our opened key." << endl;
    }else{
        cout<< "Error closing key."<<endl;
        result_code += -3;
    }


    return result_code;
}

int8_t test_creation_status(LONG create_status ,LONG value_status, LONG close_status)
{
    int8_t result_code = 0;
    if (create_status == ERROR_SUCCESS ){
        cout<<"Success creating key."<< endl;
    }else{
        cout<<"Error creating key." << endl;
        result_code += -1;
    }

    if (value_status == ERROR_SUCCESS){
        cout<< "Success creating value name and value data for our new value." << endl;
    }else{
        cout<<"Error creating value name and value data for our value." << endl;
        result_code += -2;
    }

    if (close_status == ERROR_SUCCESS){
        cout << "Success closing our opened key." << endl;
    }else{
        cout<< "Error closing key."<<endl;
        result_code += -3;
    }
    return result_code;
}

