#import "AppDelegate.h"

#import <React/RCTBundleURLProvider.h>
#import <CodePush/CodePush.h>

static UIView *_loadingView = nil;

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  self.moduleName = @"BerlatihKosakataArab";  
  // You can add your custom initial props in the dictionary below.
  // They will be passed down to the ViewController used by React Native.
  self.initialProps = @{};

  return [super application:application didFinishLaunchingWithOptions:launchOptions];;
}

- (NSURL *)sourceURLForBridge:(RCTBridge *)bridge
{
  return [self getBundleURL];
}

- (NSURL *)getBundleURL
{
  #if DEBUG
    return [[RCTBundleURLProvider sharedSettings] jsBundleURLForBundleRoot:@"index"];
  #else
    return [CodePush bundleURL];
    // return [[NSBundle mainBundle] URLForResource:@"main" withExtension:@"jsbundle"];
  #endif
}

- (UIView *)createRootViewWithBridge:(RCTBridge *)bridge moduleName:(NSString *)moduleName initProps:(NSDictionary *)initProps {
  UIView *rootView = [super createRootViewWithBridge:bridge
                                          moduleName:moduleName
                                           initProps:initProps];
  // Handle splashscreen
  UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"LaunchScreen" bundle:nil];
  _loadingView = [[storyboard instantiateViewControllerWithIdentifier:@"SplashScreen"] view];
  _loadingView.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
  _loadingView.frame = rootView.bounds;
  _loadingView.center = (CGPoint){CGRectGetMidX(rootView.bounds), CGRectGetMidY(rootView.bounds)};
  _loadingView.hidden = NO;
  
  rootView.backgroundColor = [UIColor colorWithRed:255 green:255 blue:255 alpha:1.0];
  
  [rootView addSubview:_loadingView];
  return rootView;
}

- (void)showSplashScreen{
  _loadingView.hidden = NO;
}

- (void)dismissSplashScreen{
  _loadingView.hidden = YES;
}

@end
