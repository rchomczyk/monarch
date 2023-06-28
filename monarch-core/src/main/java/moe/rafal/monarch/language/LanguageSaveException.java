package moe.rafal.monarch.language;

class LanguageSaveException extends IllegalStateException {

    protected LanguageSaveException(String message, Throwable cause) {
        super(message, cause);
    }

    protected LanguageSaveException(String message) {
        super(message);
    }
}
