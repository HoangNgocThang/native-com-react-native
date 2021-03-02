import React from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  TouchableOpacity,
} from 'react-native';

import {
  Header,
  LearnMoreLinks,
  Colors,
  DebugInstructions,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

import { NativeModules, Button } from 'react-native';

const App = () => {

  const onClick = () => {
    const { MyFirebaseMessagingServiceModule } = NativeModules;
    MyFirebaseMessagingServiceModule.onClickEvent();
  }

  const onClick2 = async () => {
    try {
      const { MyFirebaseMessagingServiceModule } = NativeModules;
      const res = await MyFirebaseMessagingServiceModule.onClickSendParamsEvent("thang");
      alert(res);
    } catch (error) {
      console.error(e);
    }
  }

  const onClick3 = async () => {
    try {
      const { MyFirebaseMessagingServiceModule } = NativeModules;
      const res = await MyFirebaseMessagingServiceModule.getTokenFCM();
      console.log("HHHHHHH:",res)
      alert(res);
    } catch (error) {
      console.error(e);
    }
  }

  return (
    <>
      <StatusBar barStyle="dark-content" />
      <SafeAreaView>

        <TouchableOpacity onPress={() => {
          const { CalendarModule } = NativeModules;
          // const { DEFAULT_EVENT_NAME } = CalendarModule.getConstants();
          // console.log("vc:",DEFAULT_EVENT_NAME);
          // CalendarModule.createCalendarEvent('ThangName', 'ThangLocation');

          CalendarModule.createCalendarCallBackEvent('Party',
            'My House', (eventId) => {
              console.log('YYY', eventId)
            })
        }}>
          <Text>click</Text>
        </TouchableOpacity>


        <TouchableOpacity
          onPress={onClick}
          style={{ width: 100, height: 30, backgroundColor: 'green', marginTop: 20 }}>
          <Text>Test Fcm</Text>
        </TouchableOpacity>


        <TouchableOpacity
          onPress={onClick2}
          style={{ width: 100, height: 30, backgroundColor: 'green', marginTop: 20 }}>
          <Text>Test Fcm send Param</Text>
        </TouchableOpacity>

        <TouchableOpacity
          onPress={onClick3}
          style={{ width: 100, height: 30, backgroundColor: 'green', marginTop: 20 }}>
          <Text>get token fcm</Text>
        </TouchableOpacity>
      </SafeAreaView>
    </>
  );
};

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: Colors.lighter,
  },
  engine: {
    position: 'absolute',
    right: 0,
  },
  body: {
    backgroundColor: Colors.white,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: Colors.black,
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: Colors.dark,
  },
  highlight: {
    fontWeight: '700',
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
});

export default App;
