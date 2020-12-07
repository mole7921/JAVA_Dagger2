package com.example.dagger2_java.di;


import android.app.Application;

import com.example.dagger2_java.BaseApplication;
import com.example.dagger2_java.SessionManager;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,
                AppModule.class,
                ViewModelFactoryModule.class,
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {

    SessionManager sessionManager();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }
}


/*
    依賴注入流程-
    步驟1：查找Module中是否存在創建該類的方法。
    步驟2：若存在創建類方法，查看該方法是否存在參數
        步驟2.1：若存在參數，則按從**步驟1**開始依次初​​始化每個參數
        步驟2.2：若不存在參數，則直接初始化該類實例，一次依賴注入到此結束
    步驟3：若不存在創建類方法，則查找Inject註解的構造函數，看構造函數是否存在參數
        步驟3.1：若存在參數，則從**步驟1**開始依次初​​始化每個參數
        步驟3.2：若不存在參數，則直接初始化該類實例，一次依賴注入
*/


/*
    標注說明-
    @Module:
    用來標註類，Module類裡面的方法專門提供依賴，所以我們定義一個類，用@Module註解，
    這樣Dagger2在構造類的實例的時候，就知道從哪裡去找到需要的依賴。

    @Provides:
    用來標註方法，在Module中，我們定義的方法是用這個註解，
    以此來告訴Dagger2我們想要構造對象並提供這些依賴。

    @Inject:
    用來標註對像變量或構造方法，通常在需要依賴的地方使用這個註解。換句話說，
    你用它告訴Dagger2這個類或者字段需要依賴注入，
    Dagger2就會構造一個這個類的實例並滿足他們的依賴。

    @Component:
    通常用來標註介面，Component從根本上來說就是一個注入器，也可以說是@Inject和@Module的橋樑，
    它的主要作用就是連接這兩個部分。將Module中產生的依賴對象自動注入到需要依賴實例的Container中。

    @Scope:
    標註Component和Module提供對象的方法，Dagger2可以通過自定義註解限定註解作用域，
    來管理每個對象實例的生命週期。

    @Qualifier:
    用來標註方法，當類的類型不足以鑑別一個依賴的時候，我們就可以使用這個註解標示。
    例如：在Android中，我們會需要不同類型的Context，
    所以我們就可以定義Qualifier註解@ApplicationQualifier和@ActivityQualifier，
    這樣當注入一個Context的時候，我們就可以告訴Dagger2我們想要哪種類型的Context。
 */