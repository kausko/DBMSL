import json
import sys
import os
import uuid
import base64

def clear():
    if os.name == 'nt':
        os.system('cls')
    else:
        os.system('clear')

def fread() -> list:
    with open('b6.json', 'r+') as file:
        data = {}
        try:
            data = json.load(file)
        except (IOError, json.JSONDecodeError) as e:
            pass
        return data


def fwrite(data):
    with open('b6.json', 'w') as file:
        json.dump(data, file, indent=2)


def create():
    data = fread()
    if not data:
        data['students'] = []

    id = base64.urlsafe_b64encode(uuid.uuid1().bytes).rstrip(b'=').decode('ascii')
    name = input('Name: ')
    dob = input('Date of birth: ')
    gender = input('Gender: ')
    subjects = []

    nos = int(input('How many courses would you like to add? '))

    for i in range(nos):
        course_name = input('Name of course: ')
        duration = input('Duration of course: ')
        subjects.append({
            'name': course_name,
            'duration': duration
        })
    
    data['students'].append({
        'id': id,
        'name': name,
        'dob': dob,
        'gender': gender,
        'subjects': subjects
    })

    fwrite(data)


def read():
    print(json.dumps(fread(), indent=2))


def update():
    data = fread()
    if not data:
        print('DB is empty')
        return;

    id = input("Enter user's 22 digit UUID: ")
    
    rec = None
    idx = -1

    for i in range(data['students'].__len__()):
        if id == data['students'][i]['id']:
            rec = data['students'][i]
            idx = i
            break

    print(json.dumps(rec, indent=2))
    for key in rec:
        if key == "id":
            continue
        choice = input('Modify ' + key + ' (Y/N)? ')
        if choice.lower() == 'y':
            if key == "subjects":
                nos = int(input('How many courses would you like to add? '))

                for i in range(nos):
                    course_name = input('Name of course: ')
                    duration = input('Duration of course: ')
                    rec['subjects'].append({
                        'name': course_name,
                        'duration': duration
                    })
                
                continue

            rec[key] = input('Enter new ' + key + ': ')

    data['students'][idx] = rec
    fwrite(data)


def delete():
    data = fread()
    if not data:
        print('DB is empty')
        return;

    id = input("Enter user's 22 digit UUID: ")

    data['students'] = [rec for rec in data['students'] if rec['id'] != id]

    fwrite(data)


while True:
    print("JSONDB")
    print("1. Add a student")
    print("2. Display collection")
    print("3. Update details")
    print("4. Delete student")
    print("5. Exit")
    choice = int(input("Enter your choice: "))
    switch = {
        1: create,
        2: read,
        3: update,
        4: delete,
        5: sys.exit
    }

    clear()
    try:
        print('Option', choice)
        switch[choice]()
    except (TypeError, KeyError) as e:
        print(e, 'Enter a valid choice!')

    input('\n\nPress enter to return to menu')
    clear()
