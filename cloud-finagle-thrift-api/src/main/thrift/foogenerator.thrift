namespace  java com.example.myapp.thrift

struct Foo {
    1: string bar;
    2: string bazz;
    3: i32 squirrel;
}

service FooService {
    Foo giveMeSomeFoo(i32 id);
}