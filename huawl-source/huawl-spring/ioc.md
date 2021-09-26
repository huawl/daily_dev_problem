# 1.如何解决的循环依赖
*************************************************************************

首先工厂有singletonObjects（一级缓存map：成品对象，实例化后已经设置完属性）与earlySingletonObjects（二级缓存map：半成品对象，实例化没有设置属性）；
例如sayService与userInfoService互相依赖的情况，第一次在getBean(sayService)的时候，首先去singletonObjects获取对象，明显是获取不到的，
故会继续执行下一步去earlySingletonObjects获取，明显也是获取不到的，这个时候就通过bean定义信息实例化一个sayService（注意是开辟一个内存空间），并放到earlySingletonObjects；
然后继续执行下一步去设置sayService的所有属性（包括属性userInfoService）， 设置属性userInfoService的时候也是第一次getBean(userInfoService)，会优先去singletonObjects获取对象，明显是获取不到的，
故会继续执行下一步去earlySingletonObjects获取，明显也是获取不到的，这个时候就通过bean定义信息实例化一个userInfoService（注意是开辟一个内存空间），并放到earlySingletonObjects；
截止earlySingletonObjects存在两个已经实例化开辟内存空间的对象sayService与userInfoService，singletonObjects为空；

当前已经走到getBean(userInfoService)内部,所以也要设置userInfoService所有属性，去设置userInfoService的所有属性（包括属性sayService）， 设置属性sayService的时候也是优先去singletonObjects获取对象，明显还是获取不到的，
但是继续执行下一步去earlySingletonObjects获取，这个时候因为上面已经放置了故可以获取到，所以在此userInfoService的属性sayService可以成功获取并设置到属性，
然后userInfoService的属性设置完毕之后，userInfoService对象完整了就可以把这个对象移除earlySingletonObjects并放到singletonObjects(注意在这里移除新增map的key-value并没有修改已经开辟的内存空间)；
截止earlySingletonObjects存在一个已经实例化开辟内存空间的对象sayService，singletonObjects存在一个完整对象userInfoService；

此时getBean(userInfoService)就执行完毕返回一个完整的userInfoService给sayService设置属性，sayService在此就设置完其所有属性了是个完整对象可以把这个对象移除earlySingletonObjects并放到singletonObjects;
截止earlySingletonObjects没有对象，singletonObjects存在两个完整对象userInfoService与sayService；

如果先getBean(sayService),二级缓存先有sayService再有userInfoServic，而一级缓存先有userInfoService再有sayService；

*************************************************************************