!
! Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
! 
! Please see distribution for license.
!
program testdtpsv
implicit none
integer::  n, incx, incy
parameter(n=5, incx=1,incy=1)
integer:: j, i, ptr
double precision:: A(n,n), AP(((n*(n+1))/2)), x(n), y(n), alpha, beta
character(2):: length1, length2
external dtpsv
write(length1,"(I2)")((n*(n+1))/2)
write(length2,"(I2)")n
!e some data for A
do j = 1,n
        do i = 1,n
                A(i,j) = (i-1)*n+j
        enddo
enddo

AP=-1;
!e UPLO = "U"
ptr=1
do j = 1, n
        do i = 1,j
                AP(ptr) = A( i, j )
                ptr=ptr+1
        enddo
enddo

!e set x, these are especially selected so that on solution of Ax=b, x=vector of ones.
x(1)=15;
x(2)=34;
x(3)=42;
x(4)=39;
x(5)=25;

print*,"input"
print*,"x=",x
print*,""
print*,""
do i=1,n
        write(*,"(A3)",ADVANCE="NO")"A ="
        write(*,"("//trim(adjustl(length2))//"(2X,F5.2))"),A(i,:)
enddo
print*,""
print*,""
write(*,"(A3)",ADVANCE="NO")"AP="
write(*,"("//trim(adjustl(length1))//"(2X,F5.2))"),AP(:)


call  dtpsv("U","N","N",n,AP,x,incx)

print*,""
print*,"output"
write(*,"(A2)")"x="
write(*,"("//trim(adjustl(length2))//"(2X,F8.2))"),x

end program testdtpsv