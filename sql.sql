create table employees(
    id serial primary key,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    email varchar(255) not null,
    leader_id int default null,
    foreign key (leader_id) references employees(id)
);

create table leave_quotas(
    employee_id int primary key,
    quotas int not null,
    foreign key (employee_id) references employees(id)
);

create table leave_types(
    id serial primary key,
    type varchar(255) unique
);

create table leave_request_statuses(
    id serial primary key,
    status varchar(255) unique
);

create table leave_request(
    id serial primary key,
    start_date date not null,
    end_date date not null,
    request_date date not null,
    detail varchar(255),
    employee int not null,
    type int not null,
    status int not null,
    foreign key (employee) references employees(id),
    foreign key (type) references leave_types(id),
    foreign key (status) references leave_request_statuses(id)
);
