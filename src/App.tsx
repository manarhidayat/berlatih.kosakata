import React, {useCallback, useEffect} from 'react';
import SplashScreen from './modules/SplashScreen';
import DropdownAlert from 'react-native-dropdownalert';
import DropdownAlertHolder from './services/DropdownAlertHolder';
import RootNavigation from './navigation/RootNavigation';
import LoadingModal from './components/LoadingModal';
import DownloadUpdateModal from './components/DownloadUpdateModal';

const App = props => {
  useEffect(() => {
    SplashScreen.hide();
  }, []);

  const setDropdownHolder = useCallback((ref: any) => {
    DropdownAlertHolder.setInstance(ref);
  }, []);

  return (
    <React.Fragment>
      <RootNavigation />
      <LoadingModal />
      <DropdownAlert alert={setDropdownHolder} />
      <DownloadUpdateModal />
    </React.Fragment>
  );
};

export default App;
