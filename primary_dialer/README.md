# primary-dialer

### Debugger for Flow Caller:
- Debug 1 - 63: Ready to calling.
- Debug 64 - 76: If the caller not taken by the receiver.
- Debug 77 - 90: If the receiver accept the caller.

| Debug | Class            | Method                                                                   |
|-------|------------------|--------------------------------------------------------------------------|
| 1     | InCallPresenter  | ```public static synchronized InCallPresenter getInstance()```           |
| 2     | InCallPresenter  | ```public void addListener(InCallStateListener listener)```              |
| 3     | InCallPresenter  | ```public void addIncomingCallListener(IncomingCallListener listener)``` |
| 4     | InCallPresenter  | ```public void addInCallUiListener(InCallUiListener listener)```         |
| 5     | InCallPresenter  | ```public void onCallListChange(CallList callList)```                    |
