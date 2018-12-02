 
    reids注解的使用：
    
        re: 
            注意一：写在注解上的key一定要写，否则redis会默认按照方法的参数设置为redis的key,
        cacheNames可以使用CacheConfig只写一次, 如果需要key按照方法的参数设置的话使用key = "#参数名"
            注意二： 需要缓存的对象一定要先序列化，即实现Serializable接口，生成序列ID
    
        @Cacheable(cacheNames = "product", key = "123")
        Cacheable: 这个注解在第一次访问的时候会访问到方法里面的内容，
        方法里面返回一个对象，这时候会设置一个redis，redis里面存储的就是返回的对象。
        而下一次再访问到这个方法的时候它就不会再访问方法里面了，而是从redis里面直接取出来

        CachePut: 这个注解会每次把返回的内容(对象)，设置到redis里面去。

        CacheEvit: 清除缓存。再访问方法之后，会把缓存给清理掉. 适用于增删改数据之后清除缓存，然后使用Cacheable再次储存新的数据到缓存，
        保存缓存中的数据的新鲜
        
        @CacheConfig(cacheNames = ""): 作用域，可以只写一份注解，然后此类的Cacheable里面就不需要写cacheNames了
         
     条件缓存：
        @Cacheable(cacheNames = "product", key = "#参数名", condition = "#参数名.length() > 3")
        condition: 设置条件，如果参数达到这个条件，它才会对结果缓存。条件不成立则不缓存
        
     依据结果缓存：
        @Cacheable(cacheNames = "product", key = "#参数名", condition = "#参数名.length() > 3", unless = "#result.方法名() != 0")
        unless: 意思：如果不，表否定。这里的result就是获取返回的结果对象，按照对结果的条件判断，这里两个都取反(unless, !=)才是==
        
        
        