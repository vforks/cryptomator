package org.cryptomator.ui.forgetPassword;

import dagger.BindsInstance;
import dagger.Lazy;
import dagger.Subcomponent;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.cryptomator.common.vaults.Vault;
import org.cryptomator.ui.common.FxmlFile;
import org.cryptomator.ui.common.FxmlScene;

import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

@ForgetPasswordScoped
@Subcomponent(modules = {ForgetPasswordModule.class})
public interface ForgetPasswordComponent {

	@ForgetPassword
	ReadOnlyBooleanProperty confirmedProperty();

	@ForgetPassword
	Stage window();

	@FxmlScene(FxmlFile.FORGET_PASSWORD)
	Lazy<Scene> scene();

	default CompletableFuture<Boolean> showForgetPassword() {
		CompletableFuture<Boolean> result = new CompletableFuture<>();
		Stage stage = window();
		stage.setScene(scene().get());
		stage.sizeToScene();
		stage.show();
		stage.setOnHidden(evt -> result.complete(confirmedProperty().get()));
		return result;
	}

	@Subcomponent.Builder
	interface Builder {

		@BindsInstance
		Builder vault(@ForgetPassword Vault vault);

		@BindsInstance
		Builder owner(@Named("forgetPasswordOwner") Stage owner);

		ForgetPasswordComponent build();
	}

}
